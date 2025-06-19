package com.gopay.money.aggregation.application.service;

import com.gopay.common.UseCase;
import com.gopay.money.aggregation.application.port.in.GetMoneySumByAddressCommand;
import com.gopay.money.aggregation.application.port.in.GetMoneySumByAddressUseCase;
import com.gopay.money.aggregation.application.port.out.GetMembershipPort;
import com.gopay.money.aggregation.application.port.out.GetMoneySumPort;
import com.gopay.money.aggregation.application.port.out.MemberMoney;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetMoneySumByAggregationService implements GetMoneySumByAddressUseCase {

    private final GetMoneySumPort getMoneySumPort;
    private final GetMembershipPort getMembershipPort;

    @Override
    public int getMoneySumByAddress(GetMoneySumByAddressCommand command) {
        // Aggregation 을 위한 비즈니스 로직
        // 강남구, 서초구, 관악구
        String targetAddress = command.getAddress();
        List<String> membershipIds = getMembershipPort.getMembershipByAddress(targetAddress);

        // n명의 고객을 한번에 호출하게되면 성능 이슈를 발생 시킬수 있다. (부하)
        // max 100, 500 ~ api 자체에 한계를 두는 방식
        // 근데 한번에 요청 후 서비스에서 처리하는 방식이나.. 100개씩 n번 호출하는 방식이나.. 둘다 부하는 비슷할거같은데
        // 상황에 따라서 유동적으로 해야할듯
        List<List<String>> membershipPartitionList = null;
        if (membershipIds.size() > 100) {
            // 100개씩 나눠서 요청한다.
            // n명의 고객을 100명 단위로 List<List<String>>으로 만들어서 100명씩 요청을 보내기 위한 리스트
            membershipPartitionList = partitionList(membershipIds, 100);
        }

        int sum = 0;
        for (List<String> partitionedList : membershipPartitionList) {
            // 100 개씩 요청해서, 값을 계산하기로 설계.
            List<MemberMoney> memberMoneyList = getMoneySumPort.getMoneySumByMembershipIds(partitionedList);

            for (MemberMoney memberMoney : memberMoneyList) {
                sum += memberMoney.getBalance();
            }
        }

        return sum;
    }
    // List를 n개씩 묶어서 List<List<T>>로 만드는 메서드
    private static <T> List<List<T>> partitionList(List<T> list, int partitionSize) {
        return IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.groupingBy(index -> index / partitionSize))
                .values()
                .stream()
                .map(indices -> indices.stream().map(list::get).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
