package app.theblu.cashflow.cs;

import app.theblu.cashflow.cs.batteries.common.ListUtil;
import app.theblu.cashflow.cs.domain.Category;
import app.theblu.cashflow.cs.zone.bootstrap.service.CategoryService;
import app.theblu.cashflow.cs.zone.merchant.dto.MerchantCategoryVoteReq;
import app.theblu.cashflow.cs.zone.merchant.service.MerchantService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//@Service
@Slf4j
public class Benchmark {
    private final Random random = new Random();
    private final CategoryService categoryService;
    private final MerchantService merchantService;

    public Benchmark(CategoryService categoryService, MerchantService merchantService) {
        this.categoryService = categoryService;
        this.merchantService = merchantService;
    }

    @PostConstruct
    public void init() {
        var voting = 500;
        var categories = this.categoryService.getAll().stream().toList();
        var users = generateUsers();
        var merchants = generateMerchants();

//        System.out.println("Creating merchants");
//        merchants.stream().parallel().forEach(merchantService::findByFidOrCreate);

        var chunks = ListUtil.INSTANCE.splitIntoBatches(merchants, 10);

        for (var bb = 0; bb < chunks.size(); bb++) {
            var chunk = chunks.get(bb);
            var reqs = chunk
                    .stream()
                    .parallel()
                    .map(m -> randomVote(m, categories, users))
                    .flatMap(l -> l.stream())
                    .toList();

//            var temp = new LinkedList<MerchantCategoryVoteReq>();
//            for (var merchantId : chunk) {
//                var randomCategories = randomCategories(categories);
//                var randomUsers = randomUsers(users);
//                for (var userId : randomUsers) {
//                    MerchantCategoryVoteReq req = new MerchantCategoryVoteReq();
//                    req.setMerchantId(merchantId);
//                    req.setCategoryId(randomCategories.get(random.nextInt(randomCategories.size())).getId());
//                    req.setUserId(userId);
//                    temp.add(req);
//                }
//            }
            var startTime = System.currentTimeMillis();


            log.info("Creating merchant category vote batch {}/{}. req size: {}", bb, chunks.size(), reqs.size());
            reqs.stream().forEach(merchantService::voteOnCategory);
            var endTime = System.currentTimeMillis();
            var time = (endTime - startTime) / 1000;
            log.info("Time taken: {} average: {}/sec", time, reqs.size() / time);
        }
    }

    private List<MerchantCategoryVoteReq> randomVote(String merchantId, List<Category> categories, List<String> users) {
        var temp = new LinkedList<MerchantCategoryVoteReq>();
        var randomCategories = randomCategories(categories);
        var randomUsers = randomUsers(users);
        for (var userId : randomUsers) {
            MerchantCategoryVoteReq req = new MerchantCategoryVoteReq();
            req.setMerchantId(merchantId);
            req.setCategoryId(randomCategories.get(random.nextInt(randomCategories.size())).getId());
            req.setUserId(userId);
            temp.add(req);
        }
        return temp;
    }

    private List<Category> randomCategories(List<Category> categories) {
        var size = 25;
        var list = new LinkedList<Category>();
        for (int ii = 0; ii < size; ii++) {
            list.add(categories.get(random.nextInt(categories.size())));
        }
        return list;
    }

    private List<String> randomUsers(List<String> users) {
        var size = 500;
        var list = new LinkedList<String>();
        for (int ii = 0; ii < size; ii++) {
            list.add(users.get(random.nextInt(users.size())));
        }
        return list;

    }

    public List<String> generateUsers() {
        return generateStringSeq("User-{}-0000-0000-000000000000", 5_00_000);
    }

    public LinkedList<String> generateMerchants() {
        return generateStringSeq("Merchant-{}-0000-0000-000000000000", 10_00_000);
    }

    private LinkedList<String> generateUuid(int size) {
        var list = new LinkedList<String>();
        for (int ii = 0; ii < size; ii++) {
            list.add(UUID.randomUUID().toString());
        }
        return list;
    }

    private LinkedList<String> generateStringSeq(String template, int size) {
        var list = new LinkedList<String>();
        for (int ii = 0; ii < size; ii++) {
            list.add(template.replace("{}", "" + ii));
        }
        return list;
    }
}

