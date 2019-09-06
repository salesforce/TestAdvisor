package com.salesforce.cqe.execute.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.util.Strings;

import com.salesforce.selenium.support.event.AbstractWebDriverEventListener;
import com.salesforce.selenium.support.event.Step;

public class ShadowJSPathGenerator extends AbstractWebDriverEventListener {
	// taken from https://git.soma.salesforce.com/cqe/lwc-shadowpath/blob/master/devtools.js
	// and compressed by using https://javascriptcompressor.com/
	private final String shadowJSPathGeneratorScript = "function getSelector(a,long=true){if(!a.tagName)return a.nodeName;let name=a.tagName.toLowerCase();if(long){a.classList.forEach((cls)=>{name+='.'+cls.replace(/([{}])/g,'\\\\\\\\$1')})}return name}function isShadowRoot(a){return a instanceof ShadowRoot}function findPath(a){if(!a)return;const path=[];path.push({elem:a,selector:getSelector(a)});while(a!==null&&a!==document){let parent=a.parentNode;if(isShadowRoot(parent)){parent=parent.host;path.push({elem:parent,selector:getSelector(parent,false)})}a=parent}return path}function selectorFromPath(a){if(!a||a.length===0)return;let jsPath=\"document\";for(let i=a.length-1;i>=0;i--){const node=a[i];const selector=node.selector;const possibilities=eval(jsPath+\".querySelectorAll('\"+selector+\"')\")if(possibilities.length===0){console.log(\"Error: Lost my way. No valid paths from \"+jsPath);throw\"Lost my way. No valid paths from \"+jsPath;}else if(possibilities.length===1){jsPath+=\".querySelector('\"+selector+\"')\"}else{let found=false;for(let p=0;p<possibilities.length;p++){if(possibilities[p]===node.elem){jsPath+=\".querySelectorAll('\"+selector+\"')[\"+p+\"]\";found=true;break}}if(!found){console.log(\"Error: Could not find way to \"+selector)throw\"Could not find way to \"+selector;}}if(i!==0){jsPath=jsPath+'.shadowRoot'}}return jsPath}const jspath=selectorFromPath(findPath(arguments[0]));let data=Object.create(null);data.tagname=getSelector(arguments[0],true);data.jspath=jspath;data.java='String queryString = \"return '+jspath.replace(/\"/g,'\\\\\"')+'\";';return data;";
			
	private final JavascriptExecutor jsExecutor;

	public ShadowJSPathGenerator(WebDriver driver) {
		this.jsExecutor = (JavascriptExecutor) driver;
	}

	@Override
	public void closeListener() {
		; // empty implementation
	}

	@Override
	public void afterFindElementByElement(Step step, WebElement returnedElement, By by, WebElement element) {
		runScript(returnedElement, by);
	}

	@Override
	public void afterFindElementByWebDriver(Step step, WebElement returnedElement, By by) {
		runScript(returnedElement, by);
	}

	private void runScript(WebElement returnedElement, By by) {
		if (returnedElement != null) {
			String jsPath = jsExecutor.executeScript(shadowJSPathGeneratorScript, returnedElement).toString();
			if (Strings.isNotNullAndNotEmpty(jsPath)) {
				printMsg(by.toString(), jsPath);
			}
		}
	}
	
	private static void printMsg(String locator, String script) {
		System.out.printf(">>> Element inside ShadowDOM!%n>>> Locator: %s%n>>> Script: %s%n", locator, script);
	}
}
