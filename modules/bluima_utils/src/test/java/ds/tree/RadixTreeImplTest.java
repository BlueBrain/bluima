package ds.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RadixTreeImplTest {

    RadixTreeImpl<String> trie; 
    
    @Before
    public void createTree() {
        trie = new RadixTreeImpl<String>();
    }
    
    @Test
    public void testSearchForPartialParentAndLeafKeyWhenOverlapExists() {
        trie.insert("abcd", "abcd");
        trie.insert("abce", "abce");
        
        assertEquals(0, trie.searchPrefix("abe", 10).size());
        assertEquals(0, trie.searchPrefix("abd", 10).size());
    }
    
    @Test
    public void testSearchForLeafNodesWhenOverlapExists() {
        trie.insert("abcd", "abcd");
        trie.insert("abce", "abce");
   
        assertEquals(1, trie.searchPrefix("abcd", 10).size());
        assertEquals(1, trie.searchPrefix("abce", 10).size());
    }
    
    @Test
    public void testSearchForStringSmallerThanSharedParentWhenOverlapExists() {
        trie.insert("abcd", "abcd");
        trie.insert("abce", "abce");
   
        assertEquals(2, trie.searchPrefix("ab", 10).size());
        assertEquals(2, trie.searchPrefix("a", 10).size());
    }
    
    @Test
    public void testSearchForStringEqualToSharedParentWhenOverlapExists() {
        trie.insert("abcd", "abcd");
        trie.insert("abce", "abce");
   
        assertEquals(2, trie.searchPrefix("abc", 10).size());
    }
    
    @Test
    public void testInsert() {
        trie.insert("apple", "apple");
        trie.insert("bat", "bat");
        trie.insert("ape", "ape");
        trie.insert("bath", "bath");
        trie.insert("banana", "banana"); 
        
        assertEquals(trie.find("apple"), "apple");
        assertEquals(trie.find("bat"), "bat");
        assertEquals(trie.find("ape"), "ape");
        assertEquals(trie.find("bath"), "bath");
        assertEquals(trie.find("banana"), "banana");
    }
    
    @Test
    public void testInsertExistingUnrealNodeConvertsItToReal() {
        trie.insert("applepie", "applepie");
        trie.insert("applecrisp", "applecrisp");
        
        assertFalse(trie.contains("apple"));
        
        trie.insert("apple", "apple");
        
        assertTrue(trie.contains("apple"));
    }
    
    @Test
    public void testDuplicatesNotAllowed() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();

        trie.insert("apple", "apple");

        try {
            trie.insert("apple", "apple2");
            fail("Duplicate should not have been allowed");
        } catch (DuplicateKeyException e) {
            assertEquals("Duplicate key: 'apple'", e.getMessage());
        }
    }
    
    @Test
    public void testInsertWithRepeatingPatternsInKey() {
        trie.insert("xbox 360", "xbox 360");
        trie.insert("xbox", "xbox");
        trie.insert("xbox 360 games", "xbox 360 games");
        trie.insert("xbox games", "xbox games");
        trie.insert("xbox xbox 360", "xbox xbox 360");
        trie.insert("xbox xbox", "xbox xbox");
        trie.insert("xbox 360 xbox games", "xbox 360 xbox games");
        trie.insert("xbox games 360", "xbox games 360");
        trie.insert("xbox 360 360", "xbox 360 360");
        trie.insert("xbox 360 xbox 360", "xbox 360 xbox 360");
        trie.insert("360 xbox games 360", "360 xbox games 360");
        trie.insert("xbox xbox 361", "xbox xbox 361");
        
        assertEquals(12, trie.getSize());
    }

    @Test
    public void testDeleteNodeWithNoChildren() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();
        trie.insert("apple", "apple");
        assertTrue(trie.delete("apple"));
    }
    
    @Test
    public void testDeleteNodeWithOneChild() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();
        trie.insert("apple", "apple");
        trie.insert("applepie", "applepie");
        assertTrue(trie.delete("apple"));
        assertTrue(trie.contains("applepie"));
        assertFalse(trie.contains("apple"));
    }
    
    @Test
    public void testDeleteNodeWithMultipleChildren() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();
        trie.insert("apple", "apple");
        trie.insert("applepie", "applepie");
        trie.insert("applecrisp", "applecrisp");
        assertTrue(trie.delete("apple"));
        assertTrue(trie.contains("applepie"));
        assertTrue(trie.contains("applecrisp"));
        assertFalse(trie.contains("apple"));
    }
    
    @Test
    public void testCantDeleteSomethingThatDoesntExist() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();
        assertFalse(trie.delete("apple"));
    }

    @Test
    public void testCantDeleteSomethingThatWasAlreadyDeleted() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();
        trie.insert("apple", "apple");
        trie.delete("apple");
        assertFalse(trie.delete("apple"));
    }

    @Test
    public void testChildrenNotAffectedWhenOneIsDeleted() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();
        trie.insert("apple", "apple");
        trie.insert("appleshack", "appleshack");
        trie.insert("applepie", "applepie");
        trie.insert("ape", "ape");
        
        trie.delete("apple");

        assertTrue(trie.contains("appleshack"));
        assertTrue(trie.contains("applepie"));
        assertTrue(trie.contains("ape"));
        assertFalse(trie.contains("apple"));
    }
    
    @Test
    public void testSiblingsNotAffectedWhenOneIsDeleted() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();
        trie.insert("apple", "apple");
        trie.insert("ball", "ball");
        
        trie.delete("apple");
        
        assertTrue(trie.contains("ball"));
    }
    
    @Test
    public void testCantDeleteUnrealNode() {
        RadixTreeImpl<String> trie = new RadixTreeImpl<String>();
        trie.insert("apple", "apple");
        trie.insert("ape", "ape");
        
        assertFalse(trie.delete("ap"));
    }
    

    @Test
    public void testCantFindRootNode() {
        assertNull(trie.find(""));
    }

    @Test
    public void testFindSimpleInsert() {
        trie.insert("apple", "apple");
        assertNotNull(trie.find("apple"));
    }
    
    @Test
    public void testContainsSimpleInsert() {
        trie.insert("apple", "apple");
        assertTrue(trie.contains("apple"));
    }

    @Test
    public void testFindChildInsert() {
        trie.insert("apple", "apple");
        trie.insert("ape", "ape");
        trie.insert("appletree", "appletree");
        trie.insert("appleshackcream", "appleshackcream");
        assertNotNull(trie.find("appletree"));
        assertNotNull(trie.find("appleshackcream"));
        assertNotNull(trie.contains("ape"));
    }
    
    @Test
    public void testContainsChildInsert() {
        trie.insert("apple", "apple");
        trie.insert("ape", "ape");
        trie.insert("appletree", "appletree");
        trie.insert("appleshackcream", "appleshackcream");
        assertTrue(trie.contains("appletree"));
        assertTrue(trie.contains("appleshackcream"));
        assertTrue(trie.contains("ape"));
    }

    @Test
    public void testCantFindNonexistantNode() {
        assertNull(trie.find("apple"));
    }

    @Test
    public void testDoesntContainNonexistantNode() {
        assertFalse(trie.contains("apple"));
    }
    
    @Test
    public void testCantFindUnrealNode() {
        trie.insert("apple", "apple");
        trie.insert("ape", "ape");
        assertNull(trie.find("ap"));
    }

    @Test
    public void testDoesntContainUnrealNode() {
        trie.insert("apple", "apple");
        trie.insert("ape", "ape");
        assertFalse(trie.contains("ap"));
    }


    @Test
    public void testSearchPrefix_LimitGreaterThanPossibleResults() {
        trie.insert("apple", "apple");
        trie.insert("appleshack", "appleshack");
        trie.insert("appleshackcream", "appleshackcream");
        trie.insert("applepie", "applepie");
        trie.insert("ape", "ape");

        ArrayList<String> result = trie.searchPrefix("app", 10);
        assertEquals(4, result.size());

        assertTrue(result.contains("appleshack"));
        assertTrue(result.contains("appleshackcream"));
        assertTrue(result.contains("applepie"));
        assertTrue(result.contains("apple"));
    }
    
    @Test
    public void testSearchPrefix_LimitLessThanPossibleResults() {
        trie.insert("apple", "apple");
        trie.insert("appleshack", "appleshack");
        trie.insert("appleshackcream", "appleshackcream");
        trie.insert("applepie", "applepie");
        trie.insert("ape", "ape");

        ArrayList<String> result = trie.searchPrefix("appl", 3);
        assertEquals(3, result.size());

        assertTrue(result.contains("appleshack"));
        assertTrue(result.contains("applepie"));
        assertTrue(result.contains("apple"));
    }

    @Test
    public void testGetSize() {
        trie.insert("apple", "apple");
        trie.insert("appleshack", "appleshack");
        trie.insert("appleshackcream", "appleshackcream");
        trie.insert("applepie", "applepie");
        trie.insert("ape", "ape");
        
        assertTrue(trie.getSize() == 5);
    }
    
    @Test
    public void testDeleteReducesSize() {
        trie.insert("apple", "apple");
        trie.insert("appleshack", "appleshack");
        
        trie.delete("appleshack");
        
        assertTrue(trie.getSize() == 1);
    }    
    
    @Test
    public void testComplete() {
        // create a new Trie
        RadixTree<String> trie = new RadixTreeImpl<String>();
        
        trie.insert("apple", "apple");
        trie.insert("appleshack", "appleshack");
        trie.insert("applepie", "applepie");
        trie.insert("applegold", "applegold");
        trie.insert("applegood", "applegood");
        
        assertEquals("", trie.complete("z"));
        assertEquals("apple", trie.complete("a"));
        assertEquals("apple", trie.complete("app"));
        assertEquals("appleshack", trie.complete("apples"));
        assertEquals("applego", trie.complete("appleg"));
    }
}
