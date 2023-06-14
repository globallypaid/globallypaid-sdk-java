package com.globallypaid.Interface;

import com.globallypaid.enums.PaymentSourceType;
import lombok.experimental.SuperBuilder;

public interface IPaymentSource {

    PaymentSourceType Type = null;
}
