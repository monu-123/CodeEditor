package codeeditor;

import java.util.TreeSet;
/**
 *
 * @author Princy
 */
public class Trie
{
        static class TrieNode
        {
                static final int CHARACTER_LENGTH=65; // 26 alphabets and one dot
                private String keyword="";
                TrieNode children[];
                private boolean isLeafNode;
                public boolean isLeaf()
                {
                        return isLeafNode;
                }
                public void setLeaf(boolean leaf)
                {
                        this.isLeafNode=leaf;
                }
                public void setWord(String word)
                {
                        this.keyword=word;
                }
                public String getWord()
                {
                        return keyword;
                }
                public TrieNode()
                {
                        isLeafNode=false;
                        children=new TrieNode[CHARACTER_LENGTH];
                        for(int i=0;i<CHARACTER_LENGTH;i++)
                        children[i]=null;
                }
        }
	private TrieNode root=null;
	private TreeSet<String> matchKeywordList=null;
	private TreeSet<String> keywordList=null;
	public Trie(TreeSet<String> keywords)
	{
 		root=new TrieNode();
		matchKeywordList=new TreeSet<String>();
		keywordList=new TreeSet<String>();
		for(String keyword: keywords)
		{
			insert(keyword);
		}
	}
	public Trie(TreeSet<String> reserveWords,TreeSet<String> keywords)
	{
            root=new TrieNode();
		matchKeywordList=new TreeSet<String>();
		keywordList=new TreeSet<String>();
		for(String keyword: keywords)
		{
			insert(keyword);
		}
                
                for(String className: reserveWords)
		{
			insert(className);
		}
	}
	private void insert(String keyword)
	{
		TrieNode tempNode=root;
		int length=keyword.length();
		for(int i=0;i<length;i++)
		{
			int level=0;
			char currentChar=keyword.charAt(i);
			if(currentChar=='.')
                            level=52;
                        else if(currentChar=='$')
                            level=53;
                        else if(currentChar<='9'&&currentChar>='0')
                            level=currentChar-'0'+54;
			else if(currentChar=='#')
                            level=64;
                        else if(currentChar>='A'&&currentChar<='Z')
                            level=currentChar-'A';
			else
                            level=currentChar-'a'+26;
			if(tempNode==null||level>64||level<0)
                        {
                            System.out.println(keyword+" "+currentChar);
                            return;
                        }
			if(tempNode.children[level]==null)
			{
				tempNode.children[level]=new TrieNode();
			}
			tempNode=tempNode.children[level];
		}
                tempNode.setLeaf(true);
		tempNode.setWord(keyword);
	}
	public TreeSet<String> getAllPrefixMatchWord(String pattern)
	{
            matchKeywordList.clear();
		TrieNode tempNode=root;
		
                for(int i=0;i<pattern.length();i++)
		{
                    if(tempNode==null)
                        return matchKeywordList;
			int level;
			char currentChar=pattern.charAt(i);
			if(currentChar=='.')
                            level=52;
                        else if(currentChar=='$')
                            level=53;
                        else if(currentChar<='9'&&currentChar>='0')
                            level=currentChar-'0'+54;
			else if(currentChar=='#')
                            level=64;
                        
                        else if(currentChar>='A'&&currentChar<='Z')
                            level=currentChar-'A';
			else
                            level=currentChar-'a'+26;
			tempNode=tempNode.children[level];
		}
                
		getAllWord(tempNode);
		return matchKeywordList;
	}
	private void getAllWord(TrieNode tempNode)
	{
            if(tempNode==null)
                return;
		if(tempNode.isLeaf())
		matchKeywordList.add(tempNode.getWord());
		
		for(int i=0;i<tempNode.CHARACTER_LENGTH;i++)
		{
			int level;
			if(tempNode.children[i]!=null)
			getAllWord(tempNode.children[i]);
		}
	}
}