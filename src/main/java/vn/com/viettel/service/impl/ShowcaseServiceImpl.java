package vn.com.viettel.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.viettel.dto.ShowcaseDTO;
import vn.com.viettel.entity.ShowcaseEntity;
import vn.com.viettel.mapper.ShowcaseMapper;
import vn.com.viettel.repository.ShowcaseRepository;
import vn.com.viettel.service.ShowcaseService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowcaseServiceImpl implements ShowcaseService {

    @Autowired
    private ShowcaseRepository showcaseRepository;;

    @Autowired
    private ShowcaseMapper showcaseMapper;
    @Override
    public List<ShowcaseDTO> getAllShowcase() {
        List<ShowcaseDTO> result = new ArrayList<>();
        List<ShowcaseEntity> showcaseList = showcaseRepository.findAllShowcase();
        showcaseList.forEach((showcase) -> {
            ShowcaseDTO showcaseDTO = new ModelMapper().map(showcase, ShowcaseDTO.class);
            result.add(showcaseDTO);
        });
        return result;
    }
}
