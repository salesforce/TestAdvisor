/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

 package com.salesforce.cte.common;

/**
 * This enum defines potential test event type will be collected by all test listeners
 * SCREEN_SHOT: this event collected by screenshot listener when a screenshot was taken.
 * REFERRAL_URL: this event collected by selenium action listener when a selenium action happened. 
 *               It will collected the current referral url for current selenium action.
 * EXCEPTION: this event could collected by any listener when a exception was throw during test execution.
 * AUTOMATION: this event could collected by any listener when any test event happened.
 * 
 */
 public enum TestEventType {
    SCREEN_SHOT,
    REFERRAL_URL,
    EXCEPTION,
    AUTOMATION
}
