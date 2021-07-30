package io.company.brewcraft.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Measure;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.MeasureService;

@Transactional
public class MeasureServiceImpl extends BaseService implements MeasureService {
        
    private MeasureRepository measureRepository;
    
    public MeasureServiceImpl(MeasureRepository measureRepository) {
        this.measureRepository = measureRepository;        
    }

    @Override
    public List<Measure> getAllMeasures() { 
        List<Measure> measures = measureRepository.findAll();

        return measures;
    }
}
