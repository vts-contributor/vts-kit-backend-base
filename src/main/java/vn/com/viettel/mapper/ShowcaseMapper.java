package vn.com.viettel.mapper;

import org.mapstruct.Mapper;
import vn.com.viettel.dto.ShowcaseDTO;
import vn.com.viettel.entity.ShowcaseEntity;

@Mapper(componentModel = "spring")
public interface ShowcaseMapper {
    ShowcaseDTO toShowcaseDTO(ShowcaseEntity showcase);
}
