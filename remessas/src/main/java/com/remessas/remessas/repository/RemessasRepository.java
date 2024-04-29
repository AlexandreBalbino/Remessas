package com.remessas.remessas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.remessas.remessas.entity.Remessa;

public interface RemessasRepository extends JpaRepository<Remessa, Long> {

    @Query("select re from Remessa re where re.usuarioRemetente.id = :idUsuario AND re.dataRemessa >= :dataInicio AND re.dataRemessa <= :dataFim")
    List<Remessa> findRemessaByUsuarioAndDataInicioAndDataFim(@Param("idUsuario") long idUsuario,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);
}
