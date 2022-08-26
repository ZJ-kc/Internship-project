package org.hzero.service.api.controller.v1;

import java.util.ArrayList;
import java.util.List;

import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.service.api.dto.CardDTO;
import org.hzero.service.app.service.CardService;
import org.hzero.service.domain.entity.Card;
import org.hzero.service.domain.repository.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;

import io.swagger.annotations.ApiOperation;

/**
 *  管理 API
 *
 * @author jiao.zhao@zknown.com 2022-08-23 17:39:12
 */
@RestController("cardController.v1" )
@RequestMapping("/v1/{organizationId}/cards" )
public class CardController extends BaseController {

    private final CardService cardService;

    private final CardRepository cardRepository;


    @Autowired
    public CardController(CardService cardService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    @ApiOperation(value="查询所有card坐标信息")
    @Permission(level=ResourceLevel.ORGANIZATION, permissionLogin=true)
    @GetMapping("/card/list")
    public ResponseEntity<List<CardDTO>> listCards(@PathVariable("organizationId") Long organizationId){
        return Results.success(cardRepository.listCards());
    }

    @ApiOperation(value="批量插入坐标信息")
    @Permission(level=ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/card/add-batch")
    public ResponseEntity<?> addBatchCard(@PathVariable("organizationId") Long organizationId,
                               @RequestBody List<Card> cards){
        return cardService.addBatchCard(cards);
    }

}
