package app.theblu.cashflow.cs.zone.bootstrap.service;

import app.theblu.cashflow.cs.domain.Category;
import app.theblu.cashflow.cs.batteries.common.ClasspathUtil;
import app.theblu.cashflow.cs.batteries.common.JsonUtil;
import app.theblu.cashflow.cs.batteries.common.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CategoryService {
    private final Map<String, Category> categoryMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        readFile("config/expense-categories.yaml", Category.TrxType.EXPENSE);
        readFile("config/income-categories.yaml", Category.TrxType.INCOME);
    }

    private void readFile(String file, Category.TrxType trxType) {
        var yaml = ClasspathUtil.INSTANCE.readPathAsString(file);
        List<String> groupList = StringUtil.INSTANCE.splitBy(yaml, "##### SPLIT #####");

        for (String group : groupList) {
            if(group.trim().startsWith("#")) continue;
            var jsonRaw = JsonUtil.INSTANCE.fromYamlToJson(group);
            var jsons = JsonUtil.INSTANCE.fromJsonAsList(jsonRaw, Category.class);
            if (jsons.isEmpty()) continue;
            val parentCategory = jsons.getFirst();
            parentCategory.setTrxType(trxType);
            if (parentCategory.getDescription() == null) parentCategory.setDescription("");
            if (parentCategory.getParentId() == null) parentCategory.setParentId("");
            if(parentCategory.getExamples() == null) parentCategory.setExamples("");
            addCategoryToMap(parentCategory);
            if (jsons.size() > 1) {
                for (int ii = 1; ii < jsons.size(); ii++) {
                    val childCategory = jsons.get(ii);
                    childCategory.setParentId(parentCategory.getId());
                    childCategory.setTrxType(trxType);
                    if (childCategory.getDescription() == null) childCategory.setDescription("");
                    if(childCategory.getExamples() == null) childCategory.setExamples("");
                    addCategoryToMap(childCategory);
                }
            }
        }
    }

    private void addCategoryToMap(Category category) {
        if (categoryMap.containsKey(category.getId())) {
            throw new RuntimeException("found duplicate category for id: " + category.getId());
        }
        categoryMap.put(category.getId(), category);
    }

    @Nullable
    public Collection<Category> getAll() {
        printSQL();
        return this.categoryMap.values();
    }

    public Category findById(String id) {
        return this.categoryMap.get(id);
    }

    private void printSQL() {
        for (Category category : this.categoryMap.values()) {
            String sql = "INSERT INTO `CategoryEntity` (`id`,`parentId`,`name`,`description`,`icon`,`trxType`,`spendingPriority`,`markedAsHidden`,`defaultCategory`,`examples`) " + "VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}',{7},{8},'{9}')";

            sql = sql.replace("{0}", category.getId());
            sql = sql.replace("{1}", category.getParentId());
            sql = sql.replace("{2}", category.getName());
            sql = sql.replace("{3}", category.getDescription());
            sql = sql.replace("{4}", category.getIcon());
            sql = sql.replace("{5}", category.getTrxType().toString());
            sql = sql.replace("{6}", "NONE");
            sql = sql.replace("{7}", "0");
            sql = sql.replace("{8}", "1");
            sql = sql.replace("{9}", category.getExamples());
            System.out.println(sql);
        }
    }
}