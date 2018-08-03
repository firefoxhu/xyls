package com.xyls.conversion;
import com.xyls.model.News;
import com.xyls.dto.dto.NewsDTO;
import com.xyls.utils.RelativeDateFormatUtil;
import org.springframework.beans.BeanUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class News2DTO {

    /**
     * 将News转化为DTO
     * @param source
     * @param target
     */
    public static void  news2Dto(List<News> source, List<NewsDTO> target) {

        if(!source.isEmpty()){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            source.stream().forEach((e)->{
                NewsDTO newsDTO=new NewsDTO();
                BeanUtils.copyProperties(e,newsDTO);
                newsDTO.setImages(e.getNewsHomeThumbnail().split(","));
                try {
                    newsDTO.setAgoTime(RelativeDateFormatUtil.format(format.parse(e.getCreateTime())));
                } catch (ParseException e1) {
                    newsDTO.setAgoTime(e.getCreateTime());
                }
                target.add(newsDTO);
            });
        }

    }

    /**
     * 将DTO转化为NEWS
     * @param source
     * @param target
     */
    public static void  Dto2News(List<NewsDTO> source, List<News> target){
        if(source.isEmpty()){
            source.forEach((e)->{
                News news=new News();
                BeanUtils.copyProperties(e,news);
                target.add(news);
            });
        }
    }

}
