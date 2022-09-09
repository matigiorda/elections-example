package matias.giorda.electionsexample.dto;

import javax.validation.constraints.NotNull;

public class ElectionDTO {

    private Long id;

    @NotNull
    private String name;

    public ElectionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
