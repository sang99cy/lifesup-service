package me.sunrise.vo.response;

import me.sunrise.entity.ProductEnity;
import org.springframework.data.domain.Page;

public class CategoryPage {
    private String category;
    private Page<ProductEnity> page;

    public CategoryPage(String category, Page<ProductEnity> page) {
        this.category = category;
        this.page = page;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Page<ProductEnity> getPage() {
        return page;
    }

    public void setPage(Page<ProductEnity> page) {
        this.page = page;
    }
}
