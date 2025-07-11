package com.projects.ecommerce.Services;

import java.util.List;
import java.util.Set;

import com.projects.ecommerce.Constants.SortConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.DTO.mapper.EntityDTOMapper;
import com.projects.ecommerce.Entity.CouponsCentral;
import com.projects.ecommerce.Entity.DTO.CouponsCentralDTO;
import com.projects.ecommerce.Repository.CouponsCentralRepo;

@Service
public class CouponsCentralService {

    private final CouponsCentralRepo couponsCentralRepo;
    private final EntityDTOMapper entityDTOMapper;
    private final UtilityService utilityService;

    public CouponsCentralService (CouponsCentralRepo couponsCentralRepo, EntityDTOMapper entityDTOMapper, UtilityService utilityService) {
        this.couponsCentralRepo = couponsCentralRepo;
        this.entityDTOMapper = entityDTOMapper;
        this.utilityService = utilityService;
    }

    public CouponsCentralDTO saveCoupon(CouponsCentral couponsCentral){
        if(couponsCentralRepo.existsByCouponCode(couponsCentral.getCouponCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon code already exists");
        }
        if(couponsCentral.getCouponCode() == null || couponsCentral.getCouponCode().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon code cannot be null or empty");
        }
        else if(couponsCentral.getHeading() == null || couponsCentral.getHeading().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Heading cannot be null or empty");
        }
        else if(couponsCentral.getDescription() == null || couponsCentral.getDescription().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description cannot be null or empty");
        }
        else if(couponsCentral.getCategory() == null || couponsCentral.getCategory().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Catagory cannot be null or empty");
        }
        return entityDTOMapper.toCouponsCentralDTO(couponsCentralRepo.save(couponsCentral));
    }

    public List<CouponsCentral> getAllCoupons(){
        return couponsCentralRepo.findAll();
    
    }

    public Page<CouponsCentral> getAllCouponsPage(Pageable p){
        return couponsCentralRepo.findAll(p);
    }

    public List<CouponsCentralDTO> getAllCouponsDTO(){
        return entityDTOMapper.toCouponsCentralDTOList(getAllCoupons());
    }

    public Page<CouponsCentralDTO> getAllCouponsDTOPage(Pageable p){
        return entityDTOMapper.toCouponsCentralDTOPage(getAllCouponsPage(p));
    }

    public CouponsCentral getCouponByCouponCodeId(String id){
        return couponsCentralRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found with id: "+id));
    }

    public CouponsCentralDTO getCouponDTOByCouponId(String id){
        CouponsCentral couponsCentral=  getCouponByCouponCodeId(id);
        return entityDTOMapper.toCouponsCentralDTO(couponsCentral);
    }

    public CouponsCentralDTO updateCouponByCouponId(CouponsCentral newCoupon, String id){
        CouponsCentral existingCoupon = getCouponByCouponCodeId(id);

        updateCouponByCheckingFields(existingCoupon, newCoupon);
        try {
            return entityDTOMapper.toCouponsCentralDTO(couponsCentralRepo.save(existingCoupon));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to save coupon: " , e);
        }
    }

    private void updateCouponByCheckingFields(CouponsCentral existingCoupon, CouponsCentral newCoupon){
        if(newCoupon.getDescription() != null && !newCoupon.getDescription().isEmpty()){
            existingCoupon.setDescription(newCoupon.getDescription());
        }
        if(newCoupon.getHeading() != null && !newCoupon.getHeading().isEmpty()){
            existingCoupon.setHeading(newCoupon.getHeading());
        }
        if(newCoupon.getCategory() != null && !newCoupon.getCategory().isEmpty()){
            existingCoupon.setCategory(newCoupon.getCategory());
        }
    }

    public void deleteCouponByCouponId(String id){
        if(!couponsCentralRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found with id: "+ id);
        }
        couponsCentralRepo.deleteById(id);
    }

    public void validateSearchParamater(Pageable p) {
        Set<String> validSortFields = SortConstants.COUPON_SORT_FIELDS;
        utilityService.validateSearchParamater(p, validSortFields);
    }
}
