package gui.sinogramas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import data.sinogramas.Unihan;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private LinkedList<Unihan> unihanList;
    private Context context;

    public ListAdapter(LinkedList<Unihan> unihanList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.unihanList = unihanList;
    }

    @Override
    public int getItemCount() {
        return unihanList.size();
    }
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_sinogram_element, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(unihanList.get(position));
    }

    public void setItems(LinkedList<Unihan> unihanList) {
        this.unihanList = unihanList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sinogram, radical, meaning, pronunciation, strokes;

        ViewHolder(View itemView){
            super(itemView);
            sinogram = itemView.findViewById(R.id.sinogramText);
            radical = itemView.findViewById(R.id.radicalText);
            meaning = itemView.findViewById(R.id.meaningText);
            pronunciation = itemView.findViewById(R.id.pronunciationText);
            strokes = itemView.findViewById(R.id.strokesText);
        }

        void bindData(final Unihan item) {
            if (item!=null) {
                sinogram.setText(item.getSinogram());
                radical.setText("Radical: " + item.getRadix());
                meaning.setText("Significado: " + item.getFirstSpanishDefinition());
                pronunciation.setText("Nombre: " + item.getPinyin());
                strokes.setText("Trazos: " + String.valueOf(item.getNumOfStrokes()));
            }
        }
    }
}
