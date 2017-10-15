package de.leif.ffmanagementsuite.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName() + ".erreichbarkeitens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName() + ".emails", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName() + ".dienstzeitens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName() + ".ausbildungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName() + ".schutzausruestungs", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName() + ".fuehrerscheines", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName() + ".verfuegbarkeitens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Person.class.getName() + ".leistungspruefungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Erreichbarkeit.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Email.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Ausbildung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Lehrgang.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Lehrgang.class.getName() + ".voraussetzungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Organisationsstruktur.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Organisationsstruktur.class.getName() + ".gegliedertIns", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Dienstzeit.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Leistungspruefung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Leistungspruefung.class.getName() + ".teilnehmers", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Fuehrerschein.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Verfuegbarkeit.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Dienststellung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.InventarKategorie.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.InventarKategorie.class.getName() + ".unterkategories", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.InventarKategorie.class.getName() + ".wartungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.InventarKategorie.class.getName() + ".pruefungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Inventar.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Inventar.class.getName() + ".durchgefuehrteWartungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Inventar.class.getName() + ".durchgefuehrtePruefungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Schutzausruestung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Schutzausruestung.class.getName() + ".durchgefuehrteWartungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Schutzausruestung.class.getName() + ".durchgefuehrtePruefungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Auspraegung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Fahrzeug.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Fahrzeug.class.getName() + ".durchgefuehrteWartungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Fahrzeug.class.getName() + ".durchgefuehrtePruefungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.AtemschutzInventar.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.AtemschutzInventar.class.getName() + ".durchgefuehrteWartungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.AtemschutzInventar.class.getName() + ".durchgefuehrtePruefungens", jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Reinigung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Wartung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Pruefung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.DurchfuehrungPruefung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.DurchfuehrungWartung.class.getName(), jcacheConfiguration);
            cm.createCache(de.leif.ffmanagementsuite.domain.Berichtselement.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
