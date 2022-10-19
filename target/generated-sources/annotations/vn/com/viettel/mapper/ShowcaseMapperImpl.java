package vn.com.viettel.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.com.viettel.dto.ShowcaseDTO;
import vn.com.viettel.entity.ShowcaseEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-19T14:28:51+0700",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.14 (Oracle Corporation)"
)
@Component
public class ShowcaseMapperImpl implements ShowcaseMapper {

    @Override
    public ShowcaseDTO toShowcaseDTO(ShowcaseEntity showcase) {
        if ( showcase == null ) {
            return null;
        }

        ShowcaseDTO showcaseDTO = new ShowcaseDTO();

        return showcaseDTO;
    }
}
