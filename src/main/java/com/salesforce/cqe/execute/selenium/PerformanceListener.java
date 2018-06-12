/**
 * 
 */
package com.salesforce.cqe.execute.selenium;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.salesforce.selenium.support.event.AbstractWebDriverEventListener;
import com.salesforce.selenium.support.event.Step;

public class PerformanceListener extends AbstractWebDriverEventListener {
	private Step lastStep = null;

	public void closeListener() {
		; // empty implementation
	}

	@Override
	public void beforeClose(Step step) {
		beforeActionNoParams(step);
	}

	@Override
	public void afterClose(Step step) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeGet(Step step, String url) {
		beforeActionOneStringParam(step);
	}

	@Override
	public void afterGet(Step step, String url) {
		afterActionNoReturnValue(step);
	}
	@Override
	public void beforeFindElementByElement(Step step, By by, WebElement element) {
		beforeGatherOneParam(step);
	}

	@Override
	public void afterFindElementByElement(Step step, WebElement returnedElement, By by, WebElement element) {
		String result = "Step " + step.getStepNumber() + ": executed in " + Step.formattedNanoTime(step.getTimeElapsedStep())
			+ " and returned: WebElement [" + step.getReturnValue() + "]";
		System.out.println(result);
	}

	@Override
	public void beforeFindElementByWebDriver(Step step, By by) {
		beforeGatherOneParam(step);
	}

	@Override
	public void afterFindElementByWebDriver(Step step, WebElement returnedElement, By by) {
		String result = "Step " + step.getStepNumber() + ": executed in " + Step.formattedNanoTime(step.getTimeElapsedStep())
				+ " and returned: WebElement [" + step.getReturnValue() + "]";
		System.out.println(result);
	}

	@Override
	public void beforeGetWindowHandle(Step step) {
		beforeGatherNoParams(step);
	}

	@Override
	public void afterGetWindowHandle(Step step, String handle) {
		String result = "Step " + step.getStepNumber() + ": executed in " + Step.formattedNanoTime(step.getTimeElapsedStep())
		+ " and returned: Window ID [" + step.getReturnValue() + "]";
		System.out.println(result);
	}

	@Override
	public void beforeGetWindowHandles(Step step) {
		beforeGatherNoParams(step);
	}

	@Override
	public void afterGetWindowHandles(Step step, Set<String> handles) {
		String result = "Step " + step.getStepNumber() + ": executed in " + Step.formattedNanoTime(step.getTimeElapsedStep())
		+ " and returned: Window ID's [" + step.getReturnValue() + "]";
		System.out.println(result);
	}

	@Override
	public void beforeQuit(Step step) {
		beforeActionNoParams(step);
	}

	@Override
	public void afterQuit(Step step) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeTo(Step step, String url) {
		beforeActionOneStringParam(step);
	}

	@Override
	public void afterTo(Step step, String url) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeDefaultContent(Step step) {
		beforeActionNoParams(step);
	}

	@Override
	public void afterDefaultContent(Step step) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeFrameByName(Step step, String frameName) {
		beforeActionOneStringParam(step);
	}

	@Override
	public void afterFrameByName(Step step, String frameName) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeMaximize(Step step) {
		beforeActionNoParams(step);
	}

	@Override
	public void afterMaximize(Step step) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeWindow(Step step, String windowName) {
		beforeActionOneStringParam(step);
	}

	@Override
	public void afterWindow(Step step, String windowName) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeClick(Step step, WebElement element) {
		beforeActionOneWebElementParam(step);
	}

	@Override
	public void afterClick(Step step, WebElement element) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeClear(Step step, WebElement element) {
		beforeActionOneWebElementParam(step);
	}

	@Override
	public void afterClear(Step step, WebElement element) {
		afterActionNoReturnValue(step);
	}

	@Override
	public void beforeGetText(Step step, WebElement element) {
		beforeGatherOneParam(step);
	}

	@Override
	public void afterGetText(Step step, String text, WebElement element) {
		String result = "Step " + step.getStepNumber() + ": executed in " + Step.formattedNanoTime(step.getTimeElapsedStep())
				+ " and returned: '" + step.getReturnValue() + "'";
		System.out.println(result);
	}

	@Override
	public void beforeSendKeys(Step step, WebElement element, CharSequence... keysToSend) {
		if (step.getStepNumber() > 1) {
			if (lastStep != null) {
				System.out.println("Step " + step.getStepNumber() + ": Time elapsed since action '" + lastStep.getCmd().getShortCmdString() + "' in step " + lastStep.getStepNumber() + ": " + Step.formattedNanoTime(step.getTimeSinceLastAction()));
			} else {
				System.out.println("Step " + step.getStepNumber() + ": Time elapsed between actions: " + Step.formattedNanoTime(step.getTimeSinceLastAction()));
			}
		}
		String result = "Step " + step.getStepNumber() + ": action '" + step.getCmd().getLongCmdString() + "(" + step.getParam1() + ", \"" + step.getParam2() + "\")'";
		System.out.println(result);
	}

	@Override
	public void afterSendKeys(Step step, WebElement element, CharSequence... keysToSend) {
		afterActionNoReturnValue(step);
	}

	private void beforeActionNoParams(Step step) {
		if (step.getStepNumber() > 1) {
			if (lastStep != null) {
				System.out.println("Step " + step.getStepNumber() + ": Time elapsed since action '" + lastStep.getCmd().getShortCmdString() + "' in step " + lastStep.getStepNumber() + ": " + Step.formattedNanoTime(step.getTimeSinceLastAction()));
			} else {
				System.out.println("Step " + step.getStepNumber() + ": Time elapsed between actions: " + Step.formattedNanoTime(step.getTimeSinceLastAction()));
			}
		}
		String result = "Step " + step.getStepNumber() + ": action '" + step.getCmd().getLongCmdString() + "()'";
		System.out.println(result);
	}

	private void beforeActionOneWebElementParam(Step step) {
		String cmdPrefix = step.getCmd().getLongCmdString();
		if (step.getStepNumber() > 1) {
			if (lastStep != null) {
				System.out.println("Step " + step.getStepNumber() + ": Time elapsed since action '" + lastStep.getCmd().getShortCmdString() + "' in step " + lastStep.getStepNumber() + ": " + Step.formattedNanoTime(step.getTimeSinceLastAction()));
			} else {
				System.out.println("Step " + step.getStepNumber() + ": Time elapsed between actions: " + Step.formattedNanoTime(step.getTimeSinceLastAction()));
			}
		}
		String result = "Step " + step.getStepNumber() + ": action '" + cmdPrefix + "(" + step.getParam1() + ")'";
		System.out.println(result);
	}

	private void beforeActionOneStringParam(Step step) {
		String cmdPrefix = step.getCmd().getLongCmdString();
		if (step.getStepNumber() > 1) {
			if (lastStep != null) {
				System.out.println("Step " + step.getStepNumber() + ": Time elapsed since action '" + lastStep.getCmd().getShortCmdString() + "' in step " + lastStep.getStepNumber() + ": " + Step.formattedNanoTime(step.getTimeSinceLastAction()));
			} else {
				System.out.println("Step " + step.getStepNumber() + ": Time elapsed between actions: " + Step.formattedNanoTime(step.getTimeSinceLastAction()));
			}
		}
		String result = "Step " + step.getStepNumber() + ": action '" + cmdPrefix + "(\"" + step.getParam1() + "\")'";
		System.out.println(result);
	}

	private void beforeGatherNoParams(Step step) {
		String result = "Step " + step.getStepNumber() + ": gather '" + step.getCmd().getLongCmdString() + "()'";
		System.out.println(result);
	}

	private void beforeGatherOneParam(Step step) {
		String result = "Step " + step.getStepNumber() + ": gather '" + step.getCmd().getLongCmdString() + "(" + step.getParam1() + ")'";
		System.out.println(result);
	}

	private void afterActionNoReturnValue(Step step) {
		lastStep = step;
		String result = "Step " + step.getStepNumber() + ": action '" + step.getCmd().getShortCmdString() + "' executed in " + Step.formattedNanoTime(step.getTimeElapsedStep());
		System.out.println(result);
	}
}
