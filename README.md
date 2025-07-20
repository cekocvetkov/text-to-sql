> 📖 **Read the full article on Medium:**  
> [Building a Text-to-SQL AI Agent with pure Java, LangChain4j, and Ollama: Ask your database anything](https://itnext.io/building-a-text-to-sql-ai-agent-with-pure-java-langchain4j-and-ollama-ask-your-database-anything-5a643f9df632)  
> How about asking your application questions about stuff in your database?



Run db locally
```
docker run --name tts-db \
-e POSTGRES_USER=tts-user \
-e POSTGRES_PASSWORD=tts-pass \
-e POSTGRES_DB=tts-db \
-p 5432:5432 -d postgres
```
