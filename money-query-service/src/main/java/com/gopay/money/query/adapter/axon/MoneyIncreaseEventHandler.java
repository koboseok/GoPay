package com.gopay.money.query.adapter.axon;

import com.gopay.common.event.RequestFirmbankingFinishedEvent;
import com.gopay.money.query.application.port.out.GetMemberAddressInfoPort;
import com.gopay.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import com.gopay.money.query.application.port.out.MemberAddressInfo;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class MoneyIncreaseEventHandler {



    @EventHandler
    public void handler(RequestFirmbankingFinishedEvent event
            , GetMemberAddressInfoPort getMemberAddressInfoPort
            , InsertMoneyIncreaseEventByAddress insertMoneyIncreaseEventByAddress
    ) {
        System.out.println("Money Increase Event Received: "+ event.toString());

        // 고객의 주소 정보
        MemberAddressInfo memberAddressInfo = getMemberAddressInfoPort.getMemberAddressInfo(event.getMembershipId());

        // Dynamodb Insert!
        String address = memberAddressInfo.getAddress(); // "강남구"
        int moneyIncrease = event.getMoneyAmount(); // "1000"
        System.out.println("Dynamodb Insert: " + address + ", " + moneyIncrease);

        insertMoneyIncreaseEventByAddress.insertMoneyIncreaseEventByAddress(address, moneyIncrease);
    }
}
