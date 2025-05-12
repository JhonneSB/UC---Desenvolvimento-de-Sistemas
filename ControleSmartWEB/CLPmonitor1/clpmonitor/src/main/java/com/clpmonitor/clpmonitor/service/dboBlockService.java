package com.clpmonitor.clpmonitor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clpmonitor.clpmonitor.model.dboBlock;
import com.clpmonitor.clpmonitor.repository.dboBlockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

@Service
public class dboBlockService
{
    @Autowired
    private dboBlockRepository blockRepository;

    public dboBlock cadastrarBloco(dboBlock block)
    {
        return blockRepository.save(block);
    }

    public void atualizarBlocos(List<dboBlock> blockList)
    {
        for (dboBlock incomingBlock : blockList)
        {
            if (incomingBlock.getId() != null && blockRepository.existsById(incomingBlock.getId()))
            {
                dboBlock existingBlock = blockRepository.findById(incomingBlock.getId()).get();
                existingBlock.setColor(incomingBlock.getColor());
                existingBlock.setStorageId(incomingBlock.getStorageId());
                existingBlock.setProductionOrder(incomingBlock.getProductionOrder());
                existingBlock.setPosition(incomingBlock.getPosition());
                blockRepository.save(existingBlock);
            } else {
                incomingBlock.setId(null);
                blockRepository.save(incomingBlock);
            }
        }
    }
    

    public List<dboBlock> listarBlocos()
    {
        return blockRepository.findAll();
    }

    public dboBlock buscarPorId(Long id)
    {
        Optional<dboBlock> bloco = blockRepository.findById(id);
        return bloco.orElseThrow(() -> new RuntimeException("Bloco não encontrado!"));
    }

    public void deletarPorId(Long id)
    {
        blockRepository.deleteById(id);
    }
}