/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:35
 */

package buying.tickets.touch.model;

import io.realm.RealmObject;
/**
 * Created by Sebastian Paciorek
 */
public class Ticket extends RealmObject implements Comparable{

    private int id;
    private String name;
    private String price;
    private String date;

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        int compareID = ((Ticket)o).getId();

        //for ascending
        //return this.getId() - compareID;

        //for descending
        return compareID - this.getId();
    }
}
