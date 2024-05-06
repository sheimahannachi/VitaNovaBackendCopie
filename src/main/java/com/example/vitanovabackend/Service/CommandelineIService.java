package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Commandeline;

import java.util.List;

public interface CommandelineIService {
    public List<Commandeline> getCommandLine();
    public Commandeline getCommandelineById(Long commandelineId);
    public void updateCommandelineQuantity(Long commandelineId, int newQuantity);
    public void processCheckout(List<Commandeline> commandelines);

}
