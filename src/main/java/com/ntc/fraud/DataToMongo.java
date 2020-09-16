package com.ntc.fraud;
 

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataToMongo {
    public static void main(String[] args) {
        MongoClient mongo = new MongoClient("192.168.56.113", 27017);
        MongoDatabase db = mongo.getDatabase("Fraud");
        MongoCollection<Document> collection = db.getCollection("Blacklist");
        File file = new File("D:/Fraud_Domain.txt");
        BufferedReader reader = null;
        List<String> head = new ArrayList<>();
        //column name
        head.add("domainName");
        List<Document> docs = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                Document document = new Document();
                String[] split = tempString.split("\t\t");
                int length = split.length;
                System.out.println(tempString);
                for (int i = 0; i < head.size(); i++) {
                    document.put(head.get(i), split[i]);
                }
                docs.add(document);
            }
            reader.close();
            collection.insertMany(docs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
