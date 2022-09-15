service datadog-agent start

java -javaagent:/var/app/dd-java-agent.jar -DnewsData.apiKey=${NEWS_DATA_API_KEY} \
-jar /var/app/newsapi-0.0.1-SNAPSHOT.jar