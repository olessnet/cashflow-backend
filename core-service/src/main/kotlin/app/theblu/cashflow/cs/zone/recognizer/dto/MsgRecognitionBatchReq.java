package app.theblu.cashflow.cs.zone.recognizer.dto;

import app.theblu.cashflow.cs.domain.Msg;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class MsgRecognitionBatchReq {
    @Valid
    private List<Msg> reqBatch;
}
