package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Model.PizzaType;
import com.ngu.pizzamania.Repository.PizzaTypeRepository;
import com.ngu.pizzamania.Service.PizzaTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PizzaTypeServiceImpl implements PizzaTypeService {

    private final PizzaTypeRepository pizzaTypeRepository;
    @Override
    public PizzaType save(PizzaType pizzaType) {
        return pizzaTypeRepository.save(pizzaType);
    }

    @Override
    public Optional<PizzaType> getPizzaTypeById(Integer id) {
        return pizzaTypeRepository.findById(id);
    }

    @Override
    public List<PizzaType> getAllPizzaTypes() {
        return pizzaTypeRepository.findAll();
    }

    @Override
    public void DeletePizzaTypeById(Integer id) {
        PizzaType pizzaType = pizzaTypeRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Does not found any pizzaType with Id ->"+id));
        pizzaTypeRepository.delete(pizzaType);
    }

    @Override
    public boolean findByPizzaTypeName(String name) {
        return pizzaTypeRepository.existsByName(name);
    }
}
