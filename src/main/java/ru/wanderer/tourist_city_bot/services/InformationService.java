package ru.wanderer.tourist_city_bot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wanderer.tourist_city_bot.repositories.InformationRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;

    public boolean findById(Long id) {
        return informationRepository.findInformationById(id).isPresent();
    }

    @Transactional
    public boolean update(Long id, String text) {
        return informationRepository.findInformationById(id)
                .map(entity -> {
                    entity.setText(text);
                    informationRepository.saveAndFlush(entity);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public boolean delete(Long id) {
        return informationRepository.findInformationById(id)
                .map(entity -> {
                    informationRepository.delete(entity);
                    informationRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
