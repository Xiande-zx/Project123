package com.example.proyectofinal.clase;

public class Service {

    private Integer id;
    private String type;
    private String description;

    private Integer idEmp;

    public Service(){

    }

    public Service(Integer id, String type, String description, Integer idEmp) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.idEmp = idEmp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Integer idEmp) {
        this.idEmp = idEmp;
    }
}
