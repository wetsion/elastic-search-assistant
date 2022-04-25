[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/wetsion/elastic-search-assistant/blob/main/LICENSE)

# Elastic Search Assistant

Assistant for Elastic Search. You can easily construct elastic-search query statement.


## quick start

```java
Bool bool = new Bool();
bool.filter(new Terms<>("age", ImmutableList.of(20, 21)));
String query = ElasticSearchAssistant.newBuilder().query(bool).from(0).size(100).toString();
System.out.println(query);
```

result:
```json
{"size":100,"query":{"bool":{"filter":[{"terms":{"age":[20,21]}}]}},"from":0}
```
