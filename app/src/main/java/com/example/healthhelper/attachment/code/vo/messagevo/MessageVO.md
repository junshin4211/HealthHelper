# MessageVO
## `result` property
In this property,

```
@SerializedName("result")
    val result:String, 
```

It may be one of followings.
1. null iff Gson can not find the field `result` when deserialization.
2. Boolean value. 
+ `true` indicates the request is handled successfully (i.e. without no error and exception).
+ `false`, otherwise.
3. String value that contains `[]`.
+ It may occur iff the response of `result` is an `ArrayList` type.
