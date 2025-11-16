package app.theblu.cashflow.cs.batteries.common

import lombok.SneakyThrows
import org.springframework.core.io.ClassPathResource
import org.springframework.util.FileCopyUtils
import java.nio.charset.StandardCharsets

object ClasspathUtil {
    @SneakyThrows
    fun readPathAsString(path: String): String {
        val classPathResource = ClassPathResource(path)
        val data = FileCopyUtils.copyToByteArray(classPathResource.getInputStream())
        return String(data, StandardCharsets.UTF_8)
    }
}