/**
 * 
 */
package com.lucene;

import java.io.IOException;
import java.nio.file.Paths;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

/**
 * @author Airey
 * @date   2017年8月15日
 */
public class TestLucene {
	@Test
	public void test() throws IOException {
		// 创建一个文档对象，相当于数据库表中的一条记录
		Document document = new Document();
		document.add(new LongField("id", 562379L, Store.YES));
		document.add(new TextField("title", "三星 W999 黑色 电信3G手机 双卡双待双通", Store.YES));
		document.add(new TextField("sellPoint", "下单送12000毫安移动电源！双3.5英寸魔焕炫屏，以非凡视野纵观天下时局，尊崇翻盖设计，张弛中，尽显从容气度！", Store.YES));
		document.add(new DoubleField("price", 42990.00, Store.YES));
		document.add(new IntField("num", 9999, Store.YES));
		document.add(new StringField("image",
				"http://image.jt.com/images/1.jpg,http://image.jt.com/images/2.jpg,"
						+ "http://image.jt.com/images/3.jpg,http://image.jt.com/images/4.jpg,"
						+ "http://image.jt.com/images/5.jpg",
				Store.YES));
		Analyzer analyzer = new StandardAnalyzer();// 默认标准，基于英文分词
		// 创建配置对象
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		// 创建一个存储的目录，当前工程下的index目录
		Directory dir = FSDirectory.open(Paths.get("./index"));
		IndexWriter writer = new IndexWriter(dir, config);
		writer.addDocument(document); // 要写入的内容增加到writer对象，创建索引
		writer.close();
		dir.close();

	}
   
	@Test//在索引上查询
	public void search() throws IOException{
		//必须和索引目录一致
		Directory dir=FSDirectory.open(Paths.get("./index"));
		IndexSearcher searcher=new IndexSearcher(DirectoryReader.open(dir));
		Query query=new TermQuery(new Term("title","三星"));//查询表达式
		//查询，返回10条记录
		TopDocs topDocs=searcher.search(query, 10);
		
	}

}
