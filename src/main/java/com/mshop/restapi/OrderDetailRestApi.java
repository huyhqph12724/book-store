package com.mshop.restapi;

import com.mshop.entity.OrderDetail;
import com.mshop.entity.Product;
import com.mshop.repository.OrderDetailRepository;
import com.mshop.repository.OrderRepository;
import com.mshop.repository.ProductResository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/order-detail")
public class OrderDetailRestApi {
    @Autowired
    OrderDetailRepository repo;

    @Autowired
    OrderRepository Orepo;

    @Autowired
    ProductResository productResository;

    @GetMapping("{id}")
    public ResponseEntity<OrderDetail> get(@PathVariable("id") Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderDetail> put(@PathVariable("id") Long id, @RequestBody OrderDetail orderDetail) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (id != orderDetail.getId()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.save(orderDetail));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailByOrder(@PathVariable("id") Long id) {
        if (!Orepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repo.findOrderDetailByOrderId(id));
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<OrderDetail> post(@RequestBody OrderDetail orderDetail) {
        if (repo.existsById(orderDetail.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Product product = productResository.findByIdAndStatusTrue(orderDetail.getProduct().getProductId());
        if (product == null) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
        if (product.getQuantity() < orderDetail.getQuantity()) {
            throw new RuntimeException("Số lượng không được vượt quá số lượng trong kho: " + product.getQuantity());
        }
        product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
        productResository.save(product);
        return ResponseEntity.ok(repo.save(orderDetail));
    }

}
