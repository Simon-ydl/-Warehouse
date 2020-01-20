package com.yjs.bean.enclosure;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "ro_enclosure")
public class Enclosure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "enclosure_name")
    private String enclosureName;
    @Column(name = "enclosure_url")
    private String enclosureUrl;
}
