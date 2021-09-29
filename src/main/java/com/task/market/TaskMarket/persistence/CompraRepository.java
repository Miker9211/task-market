package com.task.market.TaskMarket.persistence;

import com.task.market.TaskMarket.domain.Purchase;
import com.task.market.TaskMarket.domain.repository.PurchaseRepository;
import com.task.market.TaskMarket.persistence.crud.CompraCrudRepository;
import com.task.market.TaskMarket.persistence.entity.Compra;
import com.task.market.TaskMarket.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;
    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public List<Purchase> getAll() {
        return purchaseMapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> purchaseMapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = purchaseMapper.toCompra(purchase);
        compra.getComprasProductos().forEach(producto-> producto.setCompra(compra));

        return purchaseMapper.toPurchase(compraCrudRepository.save(compra));
    }
}