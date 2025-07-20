package com.tsvetkov.ai;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AIAssistant {
    @UserMessage("""
                You are a senior SQL engineer. Given the database schema and user question below, write a syntactically correct and schema-valid SQL SELECT query.
                
                Database Schema (Use only columns and tables listed here)
                {{schema}}
                
                Rules:
                Only valid syntax queries - meaning it must start with SELECT
                
                Only use tables and columns from the schema above — do not guess
                
                Only use SELECT statements (no INSERT, UPDATE, DELETE)
            
                Use explicit JOINs, not subqueries unless necessary
                
                Add LIMIT 100 to large result sets if not specified in the question
                
                Use aggregate functions (COUNT, SUM, etc.) only if the question requires it
                
                Return only the SQL query, no explanation, no comments
                
                The query must be valid SQL and executable without syntax errors
                
                User Question
                {{question}}
                
            """)
    String getQuery(@V("question") String question,  @V("schema") String schema);


    @UserMessage("""
            You are a data analyst. Based on the database schema, the user’s question, the SQL query and the SQL query results, generate a clear, concise, human-readable answer.
            Focus on answering the user's question directly using the data provided — do not describe the SQL or repeat the table structure. Give a straight answer.
            
            ### Database Schema
            {{schema}}
            
            ### User Question
            {{question}}
            
            ### SQL Query
            {{ query }}
            
            ### SQL Query Results
            {{results}}
            
            ### Answer
        """)
    String explainAnswer(@V("question") String question, @V("schema") String schema, @V("query") String query, @V("results") String results);
}