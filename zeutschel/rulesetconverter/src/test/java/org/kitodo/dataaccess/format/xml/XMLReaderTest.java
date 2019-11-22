/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */
package org.kitodo.dataaccess.format.xml;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Test;
import org.kitodo.dataaccess.Node;
import org.kitodo.dataaccess.storage.memory.MemoryStorage;

public class XMLReaderTest {

    @Test
    public void testXMLReader() throws Exception {
        File testfile = new File("src/test/resources/ruleset_slubdd.xml");
        int numberOfMetadataTypes = 122;
        int numberOfLegacyPersons = 24;
        int numberOfDocStrctTypes = 89;

        Node ruleset = XMLReader.toNode(testfile, MemoryStorage.INSTANCE);

        String rulesetNamespace = Namespaces.namespaceOf(ruleset.getType());
        String metadataTypeUri = Namespaces.concat(rulesetNamespace, "MetadataType");
        assertThat((int) ruleset.getByType(metadataTypeUri).countUntil(Integer.MAX_VALUE),
            is(equalTo(numberOfMetadataTypes)));

        String typeUri = Namespaces.concat(rulesetNamespace, "type");
        assertThat((int) ruleset.getByType(metadataTypeUri, typeUri, "person").countUntil(Integer.MAX_VALUE),
            is(equalTo(numberOfLegacyPersons)));

        String docStrctTypeUri = Namespaces.concat(rulesetNamespace, "DocStrctType");
        assertThat((int) ruleset.getByType(docStrctTypeUri).countUntil(Integer.MAX_VALUE),
            is(equalTo(numberOfDocStrctTypes)));
    }
}
