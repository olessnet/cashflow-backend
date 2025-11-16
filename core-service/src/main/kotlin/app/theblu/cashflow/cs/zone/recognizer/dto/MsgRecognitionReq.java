package app.theblu.cashflow.cs.zone.recognizer.dto;

import app.theblu.cashflow.cs.domain.Msg;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MsgRecognitionReq {
    @Valid
    @NotNull
    private Msg msg;
}
