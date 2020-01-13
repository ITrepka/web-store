package pl.pretkejshop.webstore.view.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pretkejshop.webstore.service.dto.*;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.*;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductViewDtoMapper {
    @Autowired
    private BrandService brandService;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private RateService rateService;
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private TagService tagService;

    public ProductViewDto toDto(ProductDto productDto) throws NotFoundException {
        BrandDto brand = productDto.getBrandId() == null ? null : brandService.getBrandById(productDto.getBrandId());
        DiscountDto discount = productDto.getDiscountId() == null ? null : discountService.getDiscountById(productDto.getDiscountId());
        Integer numberOfCopies = productDto.getProductCopiesIds() == null ? null : productDto.getProductCopiesIds().size();
        List<PhotoDto> photos = productDto.getPhotoIds() == null ? null : productDto.getPhotoIds().stream()
                .map(photoId -> {
                    try {
                        return photoService.getPhotoById(photoId);
                    } catch (NotFoundException e) {
                        throw new RuntimeException("Photo not found/Product View Dto Mapper");
                    }
                }).collect(Collectors.toList());
        List<RateDto> rates = productDto.getRatesIds() == null ? null : productDto.getRatesIds().stream()
                .map(rateId -> {
                    try {
                        return rateService.getRateById(rateId);
                    } catch (NotFoundException e) {
                        throw new RuntimeException("Rate not found/Product View Dto Mapper");
                    }
                }).collect(Collectors.toList());
        SubCategoryDto subCategory = productDto.getSubCategoryId() == null ? null :
                subCategoryService.getSubCategoryById(productDto.getSubCategoryId());
        List<TagDto> tagList = productDto.getTagListIds() == null ? null : productDto.getTagListIds().stream()
                .map(tagId -> {
                    try {
                        return tagService.getTagById(tagId);
                    } catch (NotFoundException e) {
                        throw new RuntimeException("Tag not found/Product View Dto Mapper");
                    }
                }).collect(Collectors.toList());

        Double averageRate = rates == null ? null : rates.stream().mapToInt(RateDto::getRate).average().getAsDouble();
        return ProductViewDto.builder()
                .productId(productDto.getId())
                .brand(brand)
                .name(productDto.getName())
                .description(productDto.getDescription())
                .discount(discount)
                .numberOfCopies(numberOfCopies)
                .photos(photos)
                .rates(rates)
                .averageRate(averageRate)
                .sellingPrize(productDto.getSellingPrice())
                .subCategoryDto(subCategory)
                .tagList(tagList)
                .targetGender(productDto.getTargetGender())
                .build();
    }
}
