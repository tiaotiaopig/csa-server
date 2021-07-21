package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity edu.scu.csaserver.domain.Link
 */
@Repository
public interface LinkMapper extends BaseMapper<Link> {

    List<Link> getLinkPage(Integer page, Integer limit);

    Integer getNodeAutoIncrement();
}




