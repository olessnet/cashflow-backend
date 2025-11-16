package app.theblu.cashflow.cs.batteries.common

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.google.gson.Gson
import lombok.SneakyThrows

object JsonUtil {
    private val gson: Gson = Gson()
    private val mapper: ObjectMapper = ObjectMapper()
    private var yamlReader: ObjectMapper = ObjectMapper(YAMLFactory())


    init {
        mapper.findAndRegisterModules()
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    fun <T> fromJson(json: String?, cls: Class<T?>): T? {
        return gson.fromJson<T?>(json, cls)
    }

    fun toJson(o: Any?): String? {
        return gson.toJson(o)
    }

    @SneakyThrows
    fun <T> fromJsonAsList(json: String, cls: Class<T>): MutableList<T> {
        val myObjects = mapper.readValue<MutableList<T>>(
            json,
            mapper.getTypeFactory().constructCollectionType(MutableList::class.java, cls)
        )
        return myObjects
    }

    fun fromYamlToJson(yaml: String): String {
        val obj: Any? = yamlReader.readValue(yaml, Any::class.java)
        val jsonWriter = ObjectMapper()
        return jsonWriter.writeValueAsString(obj)
    }
}