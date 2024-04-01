package com.mshop.restapi;

import com.mshop.entity.Statistical;
import com.mshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("statistical/api")
public class StatisticalRestApi {

    @Autowired
    OrderRepository repo;

    @GetMapping("{year}")
    public ResponseEntity<List<Statistical>> getStatisticalYear(@PathVariable("year") int year) {
        List<Object[]> list = repo.getStatisticalMonthYear(year);
        List<Statistical> listSta = new ArrayList<>();
        List<Statistical> listReal = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Statistical sta = new Statistical((int) list.get(i)[1], null, (Double) list.get(i)[0], BigInteger.valueOf(0));
            listSta.add(sta);
        }
        for (int i = 1; i < 13; i++) {
            Statistical sta = new Statistical(i, null, 0.0, BigInteger.valueOf(0));
            for (int y = 0; y < listSta.size(); y++) {
                if (listSta.get(y).getMonth() == i) {
                    listReal.remove(sta);
                    listReal.add(listSta.get(y));
                    break;
                } else {
                    listReal.remove(sta);
                    listReal.add(sta);
                }
            }
        }
        return ResponseEntity.ok(listReal);
    }

    @GetMapping("months")
    public ResponseEntity<List<Statistical>> getStatisticalMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        List<Object[]> list = repo.getStatisticalMonth();
        List<Statistical> listSta = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Date month;
            try {
                month = format.parse(list.get(i)[1].toString());
            } catch (Exception e) {
                return null;
            }
            Statistical sta = new Statistical(0, month, (Double) list.get(i)[0], (BigInteger) list.get(i)[4]);
            listSta.add(sta);
        }
        return ResponseEntity.ok(listSta);
    }

    @GetMapping("date")
    public ResponseEntity<List<Statistical>> getStatisticalAllDate() {
        List<Object[]> list = repo.getStatisticalDate();
        List<Statistical> listSta = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Statistical sta = new Statistical(0, (Date) list.get(i)[0], (Double) list.get(i)[1], (BigInteger) list.get(i)[2]);
            listSta.add(sta);
        }
        return ResponseEntity.ok(listSta);
    }

    @GetMapping("years")
    public ResponseEntity<List<Statistical>> getStatisticalAllYear() {
        List<Object[]> list = repo.getStatisticalYear();
        List<Statistical> listSta = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //Statistical sta = new Statistical(0, (Date) list.get(i)[1], (Double) list.get(i)[2], (BigInteger) list.get(i)[3]);
            Statistical sta = new Statistical((Integer) list.get(i)[0], null, (Double) list.get(i)[1], (BigInteger) list.get(i)[2]);
            listSta.add(sta);
        }
        return ResponseEntity.ok(listSta);
    }


    @GetMapping("year")
    public ResponseEntity<List<Integer>> getYears() {
        return ResponseEntity.ok(repo.getYears());
    }
}
