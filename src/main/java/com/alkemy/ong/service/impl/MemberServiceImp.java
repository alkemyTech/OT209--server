package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.mapper.MemberMapper;
import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.response.*;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.utility.PaginationHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImp implements MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public PageMemberResponse getMember(int offset, UriComponentsBuilder uriComponentsBuilder) {
        Page<MemberEntity> dataPage = memberRepository.findAll(PageRequest.of((offset -1), 10));
        List<MemberBasicResponse> dtos = memberMapper.memberEntityList2DTOList(dataPage.getContent());

        PaginationHelper uriUtil = new PaginationHelper(uriComponentsBuilder, dataPage.getTotalPages(), offset);

        return new PageMemberResponse(dtos, uriUtil.getUriPrev(), uriUtil.getUriNext());
    }

    @Override
    @Transactional
    public MemberResponse create(MemberRequest member) {

        MemberEntity entity = memberMapper.memberDTO2Entity(member);
        memberRepository.save(entity);

        return memberMapper.memberEntity2DTO(entity);
    }
    @Override
    public List<MemberBasicResponse> getMembers() {
        List<MemberEntity> entities = memberRepository.findAll();
        List<MemberBasicResponse> dtos = memberMapper.memberEntityList2DTOList(entities);

        return dtos;
    }
    @Override
    public boolean itExists(Long id) {
        return memberRepository.findById(id).isPresent();
    }
    @Override
    public MemberResponse update(Long id, MemberRequest member) {
        Optional<MemberEntity> entity = memberRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("Id not found");
        }
        memberMapper.memberEntityRefreshValues(entity.get(), member);
        MemberEntity entityUpdated = memberRepository.save(entity.get());
        return memberMapper.memberEntity2DTO(entityUpdated);
    }
    @Override
    public void delete(Long id)throws NotFoundException {
        if(memberRepository.findById(id).isEmpty()) {
            throw new ParamNotFound("Id not found");
        }
        memberRepository.deleteById(id);
    }
}
