package io.civis.ssm.sdk.client.fabric;

import com.google.common.io.Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class FabricConfig {

    public static Builder builder() throws IOException {
        return new Builder();
    }

    public static FabricConfig loadFromFile(String filename) throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource(filename).getFile();
        Properties defaultProps = new Properties();
        defaultProps.load(new FileInputStream(path));
        return builder()
                .setCertificateAuthorityUrl((String) defaultProps.get("certificate_authority.url"))
                .setCertificateAuthorityTlsAllowAllHostNames((String) defaultProps.get("certificate_authority.tls.allowAllHostNames"))
                .setCertificateAuthorityTlsFile((String) defaultProps.get("certificate_authority.tls.file"))
                .setPeerName((String) defaultProps.get("peer.name"))
                .setPeerUrl((String) defaultProps.get("peer.url"))
                .setPeerTlsAllowAllHostNames((String) defaultProps.get("peer.tls.allowAllHostNames"))
                .setPeerTlsPemFile((String) defaultProps.get("peer.tls.pemFile"))
                .setEventHubName((String) defaultProps.get("event_hub.name"))
                .setEventHubUrl((String) defaultProps.get("event_hub.url"))
                .setOrdererName((String) defaultProps.get("orderer_name"))
                .setOrdererUrl((String) defaultProps.get("orderer_url"))
                .setOrdererUrlTlsAllowAllHostNames((String) defaultProps.get("orderer_url.tls.allowAllHostNames"))
                .setOrdererUrlTlsPemFile((String) defaultProps.get("orderer_url.tls.pemFile"))
                .build();
    }

    private String certificateAuthorityUrl;
    private String certificateAuthorityTlsAllowAllHostNames;
    private String certificateAuthorityTlsFile;
    private String peerName;
    private String peerUrl;
    private String peerTlsAllowAllHostNames;
    private String peerTlsPemFile;
    private String eventHubName;
    private String eventHubUrl;
    private String ordererName;
    private String ordererUrl;
    private String ordererUrlTlsAllowAllHostNames;
    private String ordererUrlTlsPemFile;


    public String getCertificateAuthorityUrl() {
        return certificateAuthorityUrl;
    }

    public String getCertificateAuthorityTlsAllowAllHostNames() {
        return certificateAuthorityTlsAllowAllHostNames;
    }

    public String getPeerName() {
        return peerName;
    }

    public String getPeerUrl() {
        return peerUrl;
    }

    public String getPeerTlsAllowAllHostNames() {
        return peerTlsAllowAllHostNames;
    }

    public String getEventHubName() {
        return eventHubName;
    }

    public String getEventHubUrl() {
        return eventHubUrl;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public String getOrdererUrl() {
        return ordererUrl;
    }

    public String getOrdererUrlTlsAllowAllHostNames() {
        return ordererUrlTlsAllowAllHostNames;
    }

    public URL getOrdererUrlTlsPemFile() {
        return Resources.getResource(ordererUrlTlsPemFile);
    }

    public URL getPeerTlsPemFile() {
        return Resources.getResource(peerTlsPemFile);
    }

    public URL getCertificateAuthorityTlsFile() {
        return Resources.getResource(certificateAuthorityTlsFile);
    }

    public Properties getCaTlsProperties() throws IOException {
        Properties prop = new Properties();
        prop.setProperty("allowAllHostNames", getCertificateAuthorityTlsAllowAllHostNames());
        URL path = getCertificateAuthorityTlsFile();
        prop.setProperty("pemFile", path.getFile());
        return prop;
    }

    public Properties getPeerTlsProperties() throws IOException {
        Properties prop = new Properties();
        prop.setProperty("allowAllHostNames", getPeerTlsAllowAllHostNames());
        URL path = getPeerTlsPemFile();
        prop.setProperty("pemFile", path.getFile());
        return prop;
    }

    public Properties getOrdererTlsProperties() throws IOException {
        Properties prop = new Properties();
        prop.setProperty("allowAllHostNames", getOrdererUrlTlsAllowAllHostNames());
        prop.setProperty("pemFile", getOrdererUrlTlsPemFile().getFile());
        return prop;
    }

    public static class Builder {

        private final FabricConfig config;

        public Builder() {
            this.config = new FabricConfig();
        }

        public Builder setCertificateAuthorityUrl(String certificateAuthorityUrl) {
            config.certificateAuthorityUrl = certificateAuthorityUrl;
            return this;
        }

        public Builder setCertificateAuthorityTlsAllowAllHostNames(String certificateAuthorityTlsAllowAllHostNames) {
            config.certificateAuthorityTlsAllowAllHostNames = certificateAuthorityTlsAllowAllHostNames;
            return this;
        }

        public Builder setCertificateAuthorityTlsFile(String certificateAuthorityTlsFile) {
            config.certificateAuthorityTlsFile = certificateAuthorityTlsFile;
            return this;
        }

        public Builder setPeerName(String peerName) {
            config.peerName = peerName;
            return this;
        }

        public Builder setPeerUrl(String peerUrl) {
            config.peerUrl = peerUrl;
            return this;
        }

        public Builder setPeerTlsAllowAllHostNames(String peerTlsAllowAllHostNames) {
            config.peerTlsAllowAllHostNames = peerTlsAllowAllHostNames;
            return this;
        }

        public Builder setPeerTlsPemFile(String peerTlsPemFile) {
            config.peerTlsPemFile = peerTlsPemFile;
            return this;
        }

        public Builder setEventHubName(String eventHubName) {
            config.eventHubName = eventHubName;
            return this;
        }

        public Builder setEventHubUrl(String eventHubUrl) {
            config.eventHubUrl = eventHubUrl;
            return this;
        }

        public Builder setOrdererName(String ordererName) {
            config.ordererName = ordererName;
            return this;
        }

        public Builder setOrdererUrl(String ordererUrl) {
            config.ordererUrl = ordererUrl;
            return this;
        }

        public Builder setOrdererUrlTlsAllowAllHostNames(String ordererUrlTlsAllowAllHostNames) {
            config.ordererUrlTlsAllowAllHostNames = ordererUrlTlsAllowAllHostNames;
            return this;
        }

        public Builder setOrdererUrlTlsPemFile(String ordererUrlTlsPemFile) {
            config.ordererUrlTlsPemFile = ordererUrlTlsPemFile;
            return this;
        }

        public FabricConfig build() {
            return this.config;
        }
    }


}
