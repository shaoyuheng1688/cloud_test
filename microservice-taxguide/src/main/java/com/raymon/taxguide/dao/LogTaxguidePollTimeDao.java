package com.raymon.taxguide.dao;


import com.raymon.taxguide.model.LogTaxguidePollTime;

public interface LogTaxguidePollTimeDao {

    LogTaxguidePollTime findLogTaxguidePollTimeById(Long id);

    LogTaxguidePollTime updateLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime);

    LogTaxguidePollTime saveLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime);

    boolean deleteLogTaxguidePollTime(LogTaxguidePollTime logTaxguidePollTime);

    LogTaxguidePollTime findLogTaxguidePollTimeByTgIdAndSource(String tgId,int source);
}