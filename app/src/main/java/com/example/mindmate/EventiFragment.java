package com.example.mindmate;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import java.util.ArrayList;
import java.util.List;

public class EventiFragment extends Fragment {
    private MapView mapView;
    private RecyclerView recyclerView;
    private EventiAdapter adapter;
    private List<Evento> listaEventi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_eventi, container, false);
        recyclerView = v.findViewById(R.id.recyclerEventi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listaEventi = getEventiFittizi();
        adapter = new EventiAdapter(listaEventi);
        recyclerView.setAdapter(adapter);
        // Mappa OpenStreetMap
        mapView = v.findViewById(R.id.mapView);
        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.setMinZoomLevel(3.0);
        mapView.setMaxZoomLevel(20.0);
        if (!listaEventi.isEmpty()) {
            Evento primo = listaEventi.get(0);
            GeoPoint startPoint = new GeoPoint(primo.latitudine, primo.longitudine);
            mapView.getController().setZoom(13.0);
            mapView.getController().setCenter(startPoint);
        }
        for (Evento evento : listaEventi) {
            Marker marker = new Marker(mapView);
            marker.setPosition(new GeoPoint(evento.latitudine, evento.longitudine));
            marker.setTitle(evento.titolo);
            marker.setSubDescription(evento.descrizione);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mapView.getOverlays().add(marker);
        }
        // Permessi di rete (richiesti per osmdroid)
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.INTERNET}, 1);
        return v;
    }

    private List<Evento> getEventiFittizi() {
        List<Evento> eventi = new ArrayList<>();
        eventi.add(new Evento(
            "Laboratorio di Arte e Emozioni",
            "Un laboratorio creativo per esplorare le emozioni attraverso l’arte. Aperto a tutti! In questo laboratorio potrai sperimentare diverse tecniche artistiche guidato da esperti psicologi e artisti. Materiali forniti, non è richiesta esperienza.",
            41.9028, 12.4964));
        eventi.add(new Evento(
            "Respiro e Rilascio: Workshop di Meditazione",
            "Sessione guidata di meditazione per rilassare corpo e mente. Porta il tuo tappetino! Durante il workshop imparerai tecniche di respirazione consapevole e rilascio delle tensioni, con esercizi pratici e momenti di condivisione.",
            41.9109, 12.4818));
        eventi.add(new Evento(
            "Serata di Letture Ispirazionali",
            "Letture condivise per ispirare e motivare. Aperitivo incluso. Un incontro serale dove ascoltare e leggere insieme testi motivazionali, poesie e racconti, con spazio per il confronto e la riflessione.",
            41.8986, 12.4768));
        eventi.add(new Evento(
            "Yoga dolce per la mente",
            "Lezioni di yoga adatte a tutti i livelli per ritrovare equilibrio e serenità. Ogni incontro prevede una parte di rilassamento, esercizi di stretching e meditazione finale.",
            41.9057, 12.4823));
        eventi.add(new Evento(
            "Scrittura terapeutica",
            "Scopri il potere della scrittura per il benessere personale. Materiale fornito. Attraverso esercizi guidati, imparerai a esprimere emozioni e pensieri, favorendo la consapevolezza e la crescita personale.",
            41.9000, 12.4900));
        eventi.add(new Evento(
            "Passeggiata consapevole nel parco",
            "Un’esperienza di mindfulness all’aria aperta: cammineremo insieme nel verde, alternando momenti di silenzio, ascolto della natura e semplici esercizi di respirazione. Adatto a tutte le età.",
            41.9150, 12.4920));
        eventi.add(new Evento(
            "Caffè filosofico: dialoghi sulla felicità",
            "Un incontro informale in caffetteria per discutere insieme, guidati da un filosofo, sul tema della felicità. Ogni partecipante potrà portare la propria esperienza e confrontarsi in un clima accogliente.",
            41.9070, 12.4950));
        eventi.add(new Evento(
            "Laboratorio di Mandala e colori",
            "Un pomeriggio dedicato alla creazione di mandala con colori e materiali vari. L’attività favorisce la concentrazione, la calma e la creatività. Adatto anche a bambini accompagnati.",
            41.9035, 12.4880));
        eventi.add(new Evento(
            "Gruppo di ascolto e supporto emotivo",
            "Spazio protetto e riservato dove poter condividere le proprie emozioni e difficoltà, ascoltando e sostenendo gli altri partecipanti. Condotto da una psicologa esperta.",
            41.8995, 12.4930));
        eventi.add(new Evento(
            "Cinema e benessere: proiezione e dibattito",
            "Proiezione di un film a tema benessere psicologico, seguita da un dibattito guidato da esperti. Un’occasione per riflettere insieme sulle emozioni suscitate dal cinema.",
            41.9115, 12.4860));
        return eventi;
    }
}
