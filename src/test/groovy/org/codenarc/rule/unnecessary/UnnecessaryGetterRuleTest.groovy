/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.unnecessary

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for UnnecessaryGetterRule
 *
 * @author 'Hamlet D'Arcy'
 * @version $Revision: 329 $ - $Date: 2010-04-29 04:20:25 +0200 (Thu, 29 Apr 2010) $
 */
class UnnecessaryGetterRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == "UnnecessaryGetter"
    }

    void testSuccessScenario() {
        final SOURCE = '''
            x.get()
            x.property
            x.first
            x.firstName
            x.a
            x.getnotagetter()
            x.getURL()
            x.getClass()
            x.getProperty('key') '''
        assertNoViolations(SOURCE)
    }

    void testTwoSimpleGetters() {
        final SOURCE = '''
            x.getProperty()
            x.getPi()
        '''
        assertTwoViolations SOURCE,
                2, 'x.getProperty()', 'getProperty() can probably be rewritten as property',
                3, 'x.getPi()', 'getPi() can probably be rewritten as pi'
    }

    void testCamelCaseGetters() {
        final SOURCE = '''
            x.getFirstName()
        '''
        assertSingleViolation(SOURCE, 2, 'x.getFirstName()', 'getFirstName() can probably be rewritten as firstName')
    }

    void testSingleLetterNamedGetters() {
        final SOURCE = '''
            x.getA()
        '''
        assertSingleViolation(SOURCE, 2, 'x.getA()', 'getA() can probably be rewritten as a')
    }

    protected Rule createRule() {
        new UnnecessaryGetterRule()
    }
}