package ru.wanderer.tourist_city_bot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wanderer.tourist_city_bot.domain.entity.City;
import ru.wanderer.tourist_city_bot.domain.entity.Information;
import ru.wanderer.tourist_city_bot.domain.projections.CityWithInformationReadDto;
import ru.wanderer.tourist_city_bot.repositories.CityRepository;

import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public String findAllCities() {
        StringBuilder strBuilder = new StringBuilder(64);

        strBuilder.append("Города, о которых есть информация:");
        strBuilder.append("\n");

        cityRepository.findAllCities()
                .forEach(city -> {
                    strBuilder.append(city.getName());
                    strBuilder.append("\n");
                });

        strBuilder.append("\n");
        strBuilder.append("Выберите действие с помощью меню:");

        return strBuilder.toString();
    }

    public boolean findCityByName(String name) {
        return cityRepository.findByName(name).isPresent();
    }

    public String findInformationByCityName(String name) {
        String response = "Информация о таком городе отсутствует! Но вы можете её добавить)";
        List<CityWithInformationReadDto> informationList = cityRepository.findInformationByCityName(name);

        if (!informationList.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(informationList.size());
            response = informationList.get(index).getId() + "." + informationList.get(index).getText();
        }

        return response;
    }

    @Transactional
    public String create(String name) {
        if (cityRepository.findByName(name).isPresent()){
            return "Такой город уже сущестует!\nВы можете добавить о нём свою информацию)";
        } else {
            City city = new City();
            city.setName(name);
            cityRepository.saveAndFlush(city);
            return "Город успешно сохранён";
        }
    }

    @Transactional
    public boolean update(String name, String newName) {
        return cityRepository.findByName(name)
                .map(entity -> {
                    entity.setName(newName);
                    cityRepository.saveAndFlush(entity);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public boolean delete(String name) {
        return cityRepository.findByName(name)
                .map(entity -> {
                    cityRepository.delete(entity);
                    cityRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public boolean addInformation(String name, String newInformation) {
        Information information = new Information();
        information.setText(newInformation);

        return cityRepository.findByName(name)
                .map(entity -> {
                    entity.getInformationList().add(information);
                    cityRepository.saveAndFlush(entity);
                    return true;
                })
                .orElse(false);
    }
}
