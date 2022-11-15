package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;


import java.util.*;

import static java.lang.Integer.parseInt;

public class Prize {
    static List<Lotto> myLottos = new ArrayList<>();
    static List<List<Integer>> myLottoNumbers = new ArrayList<>();
    static List<Integer> winningLotto = new ArrayList<>();
    static List<Integer> countWinningTicket = new ArrayList<>(List.of(0, 0, 0, 0, 0));

    static final String ENTER_MONEY = "구매금액을 입력해 주세요.";
    static final String PURCHASE_LOTTO = "%d개를 구매했습니다.\n";
    static final String WINNING_NUMBER = "당첨 번호를 입력해 주세요.";
    static final String BONUS_NUMBER = "보너스 번호를 입력해 주세요.";

    static final int LOTTO_COUNT = 1000;

    public Prize() {
        int lottoCount = purchaseLotto();
        System.out.printf(PURCHASE_LOTTO, lottoCount);
        makeMyLotto(lottoCount);
        inputWinningLotto();
        compareLotto(winningLotto, myLottoNumbers);
    }

    public int purchaseLotto() {
        System.out.println(ENTER_MONEY);
        String inputMoney = Console.readLine();
        validateMoney(inputMoney);
        int money = parseInt(inputMoney);
        return money / LOTTO_COUNT;
    }

    public void makeMyLotto(Integer lottoCount) {
        for (int i = 1; i <= lottoCount; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            List<Integer> myNumber = new ArrayList<>();
            myNumber.addAll(numbers);
            Collections.sort(myNumber);
            Lotto lotto = new Lotto(myNumber);
            myLottos.add(lotto);
        }
        for (Lotto lotto : myLottos) {
            System.out.println(lotto.getLotto());
            myLottoNumbers.add(lotto.getLotto());
        }
    }

    public void inputWinningLotto() {
        System.out.println(WINNING_NUMBER);
        String winningNumber = Console.readLine();

        String[] splitNumber = winningNumber.split(",");
        ArrayList<String> winningNumbers = new ArrayList<>(Arrays.asList(splitNumber));

        System.out.println(BONUS_NUMBER);
        String bonusNumber = Console.readLine();

        winningNumbers.add(bonusNumber);
        System.out.println(winningNumbers);

        for (String number : winningNumbers) {
            winningLotto.add(Integer.valueOf(number));
        }
    }

    public void compareLotto(List<Integer> winningLotto, List<List<Integer>> myLottoNumbers) {
        for (List<Integer> oneLotto : myLottoNumbers) {
            int count = 0;
            int bouns = 0;
            for (Integer oneNumber : oneLotto) {
                if (winningLotto.subList(0, 6).contains(oneNumber)) {
                    count += 1;
                }
                if (winningLotto.get(6) == oneNumber) {
                    bouns += 1;
                }
            }
            Integer hit = Hit.valueOfLabel(String.valueOf(count), String.valueOf(bouns));
            if (hit != null) countWinningTicket.set(hit, countWinningTicket.get(hit) + 1);
        }
    }

    private void validateMoney(String moneyValue) {
        if (Objects.equals(moneyValue, "")) {
            throw new IllegalArgumentException("[ERROR] 구매 금액을 입력해 주세요.");
        }

        try {
            parseInt(moneyValue);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 숫자만 입력해주세요.");
        }

        int money = parseInt(moneyValue);
        if ((money % 1000) != 0) {
            throw new IllegalArgumentException("[ERROR] 입력 가능한 최소 단위는 1,000원 입니다.");
        }
    }
}
