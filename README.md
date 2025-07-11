# OneLineSQL
Perform queries using java.sql with one line of code.
## Examples
```java
boolean userExist = OneLineSQL.getBoolean(mysql, "SELECT 1 FROM users WHERE name=? AND age=?", "Alex", 21);
int age = OneLineSQL.getInt(mysql, "SELECT age FROM users WHERE user_id=?", 1234);
Integer age = OneLineSQL.getInteger(mysql, "SELECT age FROM users WHERE user_id=?", 1234);
Long uts = OneLineSQL.getLong(mysql, "SELECT uts FROM users WHERE user_id=?", 1234);
String name = OneLineSQL.getString(mysql, "SELECT name FROM users WHERE user_id=?", 1234);
byte[] photo = OneLineSQL.getBytes(mysql, "SELECT photo FROM users WHERE user_id=?", 1234);

ArrayList<Integer> userIDs = OneLineSQL.getArrayInteger(mysql, "SELECT user_id FROM users WHERE age=?", 21);
ArrayList<String> userNames = OneLineSQL.getArrayString(mysql, "SELECT name FROM users WHERE age=?", 21);

OneLineSQL.execute(mysql, "DELETE FROM users WHERE age=?", 21);
int userID = OneLineSQL.insertAutoIncrement(mysql, "INSERT INTO users(name,age) VALUES (?,?)", "Alex", 21);

HashMap<String, Object> data = new HashMap<>();
data.put("name", "Alex");
data.put("age", 21);
boolean success = OneLineSQL.insert(mysql, "users", data);

int userID = OneLineSQL.insertAutoIncrement(mysql, "users", data);
```
