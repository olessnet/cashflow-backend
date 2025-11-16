package app.theblu.cashflow.cs.domain;

import app.theblu.cashflow.cs.batteries.common.Hash;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class Msg implements Comparable<Msg> {
    @NotBlank
    private String id;
    @NotBlank
    @Size(max = 12, min = 3)
    private String header;
    @NotBlank
    private String body;
    private String location;

    @Min(value = 946665000L, message = "UTC timestamp should not be zero")
    private long timestamp;

    @JsonIgnore
    private transient String _lowerCaseBody;

    public String lowerCaseBody() {
        if (_lowerCaseBody == null) this._lowerCaseBody = body.toLowerCase();
        return _lowerCaseBody;
    }

    @JsonIgnore
    private transient String _hash;

    public String hash() {
        if (_hash == null) this._hash = Hash.INSTANCE.md5(body);
        return _hash;
    }


    public void sanitize(){
        if (body.length() > 512) {
            var temp = body.substring(0, 512);
            body = temp;
        }
    }

    @Override
    public int compareTo(@NotNull Msg o) {
        return Long.compare(this.timestamp, o.timestamp);
    }
}
