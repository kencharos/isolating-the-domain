package example.infrastructure.datasource.payroll;

import example.domain.model.contruct.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PayrollMapper {
    Integer newHourlyWageIdentifier();
    void registerHourlyWage(@Param("workerNumber") WorkerNumber workerNumber, @Param("id") Integer hourlyWageId,
                            @Param("applyDate") Date applyDate, @Param("hourlyWage") HourlyWage hourlyWage);

    List<HourlyWage> getHourlyWage(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDay") Date workDay);
}