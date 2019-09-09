// taken from https://git.soma.salesforce.com/cqe/lwc-shadowpath/blob/master/devtools.js
/*
 * Add classes to element tagname, for use in selectors.
 */
function getSelector(elem, long=true) {
    if (!elem.tagName)
        return elem.nodeName;
    let name = elem.tagName.toLowerCase();
    if (long) {
        elem.classList.forEach((cls) => {
            // Might need to add more to escape list.
            // Might be simpler to just drop class names with werid
			// characters.
            name += '.' + cls.replace(/([{}])/g, '\\\\$1');
        });
    }
    return name;
}

/*
 * Is the element at the root of a components synthetic Shadow DOM tree?
 */
function isShadowRoot(node) {
    return node instanceof ShadowRoot;
}

/*
 * Find the path from the element up to the document. The path will include
 * all LWC shadow boundaries.
 */
function findPath(node) {
    if (!node) return;
    const path = [];
    path.push({
        elem: node,
        selector: getSelector(node)
    });
    while (node !== null && node !== document) {
        let parent = node.parentNode;
        if (isShadowRoot(parent)) {
            parent = parent.host;
            path.push({
                elem: parent,
                selector: getSelector(parent, false)
            });
        }
        node = parent;
    }
    return path;
}

/**
 * Turn path into javascript selector. Use eval to select between multiple
 * possible top-down paths.
 */
function selectorFromPath(path) {
    if (!path || path.length === 0) return;
    let jsPath = "document";
    for (let i = path.length - 1; i >= 0; i--) {
        const node = path[i];
        const selector = node.selector;
        const possibilities = eval(jsPath + ".querySelectorAll('" + selector + "')")
        if (possibilities.length === 0) {
            console.log("Error: Lost my way. No valid paths from " + jsPath);
            throw "Lost my way. No valid paths from " + jsPath;
        } else if (possibilities.length === 1) { // Selector gives an
													// unique element
            jsPath += ".querySelector('" + selector + "')";
        } else {
            let found = false;
            for (let p = 0; p < possibilities.length; p++) {
                if (possibilities[p] === node.elem) {
                    jsPath += ".querySelectorAll('" + selector + "')[" + p + "]";
                    found = true;
                    break;
                }
            }
            if (!found) {
                console.log("Error: Could not find way to " + selector)
                throw "Could not find way to " + selector;
            }
        }
        // We have not reached the element yet, so add shadowRoot.
        if (i !== 0) {
            jsPath = jsPath + '.shadowRoot';
        }
    }
    return jsPath;
}

// Find the optimal selector for currently selected element.
return '@FindByJS(script = return ' + selectorFromPath(findPath(arguments[0])).replace(/"/g, '\\"') + ')';
